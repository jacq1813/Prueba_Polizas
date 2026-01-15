CREATE DATABASE PolizasDB;
USE PolizasDB;

CREATE TABLE Inventario (
    SKU INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Cantidad INT
);

CREATE TABLE Empleado (
    IdEmpleado INT PRIMARY KEY,
    Nombre VARCHAR(50),
    Apellido VARCHAR(50),
    Puesto VARCHAR(50)
);

CREATE TABLE Polizas (
    IdPoliza SERIAL PRIMARY KEY,
    Cantidad INT,
    Fecha DATE,
    EmpleadoGenero INT REFERENCES Empleado(IdEmpleado),
    SKU INT REFERENCES Inventario(SKU)
);

-- agregar para softdelete
ALTER TABLE Polizas 
ADD COLUMN Eliminada BOOLEAN DEFAULT FALSE;

-- SP CREAR POLIZA
CREATE OR REPLACE PROCEDURE sp_CrearPoliza(
    IN p_idEmpleado INT,
    IN p_sku INT,
    IN p_cantidad INT,
    IN p_fecha DATE,
    INOUT p_nuevoIdPoliza INT DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- 1. validar si hay inventario suficiente
    IF (SELECT Cantidad FROM Inventario WHERE SKU = p_sku) < p_cantidad THEN
        RAISE EXCEPTION 'No hay suficiente inventario para el SKU %', p_sku;
    END IF;

    -- 2. crear la nueva poliza y guardar el ID generado
    INSERT INTO Polizas (EmpleadoGenero, SKU, Cantidad, Fecha)
    VALUES (p_idEmpleado, p_sku, p_cantidad, p_fecha)
    RETURNING IdPoliza INTO p_nuevoIdPoliza;

    -- 3. actualizar restando el inventario
    UPDATE Inventario
    SET Cantidad = Cantidad - p_cantidad
    WHERE SKU = p_sku; 

    COMMIT;
END;
$$;

-- FUNCIÓN CONSULTAR
CREATE OR REPLACE FUNCTION sp_ConsultarPoliza(p_idPoliza INT)
RETURNS TABLE (
    id_poliza INT,
    cantidad_poliza INT,
    fecha_poliza DATE,
    nombre_empleado VARCHAR,
    apellido_empleado VARCHAR,
    sku_articulo INT,
    nombre_articulo VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        p.IdPoliza,
        p.Cantidad,
        p.Fecha,
        e.Nombre,
        e.Apellido,
        i.SKU,
        i.Nombre
    FROM Polizas p
    INNER JOIN Empleado e ON p.EmpleadoGenero = e.IdEmpleado
    INNER JOIN Inventario i ON p.SKU = i.SKU
    WHERE p.IdPoliza = p_idPoliza 
      AND p.Eliminada = FALSE; 
END;
$$;

-- SP ACTUALIZAR
CREATE OR REPLACE PROCEDURE sp_ActualizarPoliza(
    IN p_idPoliza INT,
    IN p_nuevoIdEmpleado INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_existe_poliza BOOLEAN;
    v_existe_empleado BOOLEAN;
BEGIN
    -- 1. Verificar si la poliza existe Y NO ESTÁ ELIMINADA
    SELECT EXISTS(
        SELECT 1 
        FROM Polizas 
        WHERE IdPoliza = p_idPoliza 
          AND Eliminada = FALSE 
    ) INTO v_existe_poliza;
    
    IF NOT v_existe_poliza THEN
        -- Actualizamos el mensaje de error para que sea claro
        RAISE EXCEPTION 'La póliza con ID % no existe o ha sido eliminada.', p_idPoliza;
    END IF;

    -- 2. Verificar si el nuevo empleado existe
    SELECT EXISTS(SELECT 1 FROM Empleado WHERE IdEmpleado = p_nuevoIdEmpleado) INTO v_existe_empleado;

    IF NOT v_existe_empleado THEN
        RAISE EXCEPTION 'El empleado con ID % no existe.', p_nuevoIdEmpleado;
    END IF;

    -- 3. Si paso las validaciones, hacemos el UPDATE
    UPDATE Polizas
    SET EmpleadoGenero = p_nuevoIdEmpleado
    WHERE IdPoliza = p_idPoliza;
    
    COMMIT;
END;
$$;


-- SP ELIMINAR 
CREATE OR REPLACE PROCEDURE sp_EliminarPoliza(
    IN p_idPoliza INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_existe BOOLEAN;
BEGIN
    -- 1. Validar que la poliza exista 
    SELECT EXISTS(
        SELECT 1 
        FROM Polizas 
        WHERE IdPoliza = p_idPoliza AND Eliminada = FALSE
    ) INTO v_existe;

    IF NOT v_existe THEN
        RAISE EXCEPTION 'La póliza % no existe o ya fue eliminada.', p_idPoliza;
    END IF;

    -- 2. Para no eliminar completamente simplemente cambiamos la bandera a TRUE
    UPDATE Polizas
    SET Eliminada = TRUE
    WHERE IdPoliza = p_idPoliza;
    
    COMMIT;
END;
$$;



TRUNCATE TABLE Polizas, Inventario, Empleado RESTART IDENTITY;

-- 2. INSERTAR EMPLEADOS
INSERT INTO Empleado (IdEmpleado, Nombre, Apellido, Puesto) 
VALUES 
(1, 'Juan', 'Perez', 'Vendedor Junior'),
(2, 'Maria', 'Rodriguez', 'Gerente de Ventas'),
(3, 'Carlos', 'Gomez', 'Almacenista'),
(4, 'Ana', 'Lopez', 'Auditoria');

-- 3. INSERTAR INVENTARIO (Artículos)
INSERT INTO Inventario (SKU, Nombre, Cantidad) 
VALUES 
(100, 'Laptop Dell Latitude 5420', 50),
(101, 'Mouse Logitech Inalámbrico', 200),
(1,   'Cable HDMI 2m', 150),
(2,   'Teclado Mecánico RGB', 30),
(500, 'Monitor Samsung 24 pulg', 25);

INSERT INTO Polizas (Cantidad, Fecha, EmpleadoGenero, SKU, Eliminada) 
VALUES 
(1, CURRENT_DATE, 1, 100, FALSE), 
(5, '2023-12-01', 2, 101, FALSE);

SELECT * FROM Inventario;