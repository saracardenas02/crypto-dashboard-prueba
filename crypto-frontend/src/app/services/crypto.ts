import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CryptoItem } from '../models/crypto-item.model';
import { environment } from '../../enviroments/environment';

@Injectable({ providedIn: 'root' })
export class CryptoService {

  private http = inject(HttpClient);

 private apiUrl = environment.queryServiceUrl;

  // Signals: estado reactivo de Angular 21 No se necesita ngOnChanges ni ChangeDetectorRef
  items = signal<CryptoItem[]>([]);
  selectedItem = signal<CryptoItem | null>(null);
  loading = signal<boolean>(false);
  error = signal<string | null>(null);

  loadAll(): void {
    this.loading.set(true);
    this.error.set(null);
    this.http.get<CryptoItem[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.items.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('No se pudo conectar al backend. Verifica que todos los servicios esten corriendo.');
        this.loading.set(false);
      }
    });
  }

  loadById(id: string): void {
    this.loading.set(true);
    this.error.set(null);
    this.selectedItem.set(null);
    this.http.get<CryptoItem>(`${this.apiUrl}/${id}`).subscribe({
      next: (data) => {
        this.selectedItem.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.status === 404
          ? `No se encontro la criptomoneda: ${id}`
          : 'Error al cargar el detalle.');
        this.loading.set(false);
      }
    });
  }

  clearSelected(): void {
    this.selectedItem.set(null);
    this.error.set(null);
  }

}
