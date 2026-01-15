import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ApiResponse {
  meta: { status: string };
  data: any;
}

@Injectable({
  providedIn: 'root'
})
export class PolizaService {
  // base URL
  private apiUrl = 'http://localhost:8080/api/polizas';

  constructor(private http: HttpClient) { }

  // Función para CREAR
  crear(data: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.apiUrl, data);
  }

  // Función para CONSULTAR
  consultar(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/${id}`);
  }

  // Función para ACTUALIZAR
  actualizar(data: any): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl, data);
  }

  // Función para ELIMINAR
  eliminar(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/${id}`);
  }
}