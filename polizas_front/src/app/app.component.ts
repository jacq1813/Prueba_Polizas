import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PolizaService, ApiResponse } from './services/poliza.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  seccionActual: string = 'crear'; 

  // Variables de Formulario
  polizaForm = {
    idPoliza: 0,
    idEmpleado: 0,
    sku: 0,
    cantidad: 0
  };

  idBusqueda: number | null = null;
  idEliminar: number | null = null;

  // Resultados y Mensajes
  resultado: any = null;
  mensaje: string = '';
  tipoAlerta: string = 'alert-info'; 
  constructor(private polizaService: PolizaService) {}

  // Cambiar de pestaña y limpiar formulario
  cambiarSeccion(seccion: string) {
    this.seccionActual = seccion;
    this.mensaje = '';
    this.resultado = null;
    this.polizaForm = { idPoliza: 0, idEmpleado: 0, sku: 0, cantidad: 0 };
  }

  // 1. CREAR
  grabar() {
    this.mensaje = 'Procesando...';
    this.polizaService.crear(this.polizaForm).subscribe({
      next: (res) => this.procesarRespuesta(res),
      error: (err) => this.mostrarError('Error de conexión al crear póliza')
    });
  }

  // 2. CONSULTAR
  buscar() {
    if (!this.idBusqueda) return;
    this.mensaje = 'Buscando...';
    
    this.polizaService.consultar(this.idBusqueda).subscribe({
      next: (res) => {
        if (res.meta.status === 'OK') {
          this.resultado = res.data;
          this.mensaje = ''; 
        } else {
          this.procesarRespuesta(res); 
          this.resultado = null;
        }
      },
      error: (err) => this.mostrarError('Error al consultar. Revisa si el ID existe.')
    });
  }

  // 3. ACTUALIZAR
  actualizar() {
    this.mensaje = 'Actualizando...';
    this.polizaService.actualizar(this.polizaForm).subscribe({
      next: (res) => this.procesarRespuesta(res),
      error: (err) => this.mostrarError('Error al actualizar')
    });
  }

  // 4. ELIMINAR
  eliminar() {
    if (!this.idEliminar) return;
    if(!confirm(`¿Seguro que deseas eliminar la póliza ${this.idEliminar}?`)) return;

    this.mensaje = 'Eliminando...';
    this.polizaService.eliminar(this.idEliminar).subscribe({
      next: (res) => this.procesarRespuesta(res),
      error: (err) => this.mostrarError('Error al eliminar')
    });
  }


  procesarRespuesta(res: ApiResponse) {
    if (res.meta.status === 'OK') {
      this.tipoAlerta = 'alert-success';
      const msg = res.data.Mensaje;

      this.mensaje = typeof msg === 'object' ? msg.IDMensaje : JSON.stringify(msg || 'Operación exitosa');
    } else {
      this.mostrarError(res.data.Mensaje || 'Error desconocido del servidor');
    }
  }

  mostrarError(texto: string) {
    this.tipoAlerta = 'alert-danger';
    this.mensaje = texto;
    console.error(texto);
  }
}