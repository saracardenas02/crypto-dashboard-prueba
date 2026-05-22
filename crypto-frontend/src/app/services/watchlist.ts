import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WatchlistEntry } from '../models/watchlist-entry.model';
import { environment } from '../../enviroments/environment';

@Injectable({ providedIn: 'root' })
export class WatchlistService {

  private http = inject(HttpClient);

  private apiUrl = environment.managementServiceUrl;

  entries = signal<WatchlistEntry[]>([]);
  loading = signal<boolean>(false);
  message = signal<string | null>(null);

  loadWatchlist(): void {
    this.loading.set(true);
    this.http.get<WatchlistEntry[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.entries.set(data);
        this.loading.set(false);
      },
      error: () => { this.loading.set(false); }
    });
  }

  add(cryptoId: string): void {
    this.message.set(null);
    this.http.post<WatchlistEntry>(`${this.apiUrl}/${cryptoId}`, {}).subscribe({
      next: () => {
        this.message.set(`${cryptoId} agregado a tu watchlist.`);
        this.loadWatchlist();
      },
      error: (err) => {
        this.message.set(err.status === 404
          ? 'Criptomoneda no encontrada.'
          : 'Error al agregar.');
      }
    });
  }

  remove(cryptoId: string): void {
    this.message.set(null);
    this.http.delete(`${this.apiUrl}/${cryptoId}`).subscribe({
      next: () => {
        this.message.set(`${cryptoId} eliminado de tu watchlist.`);
        this.loadWatchlist();
      },
      error: () => { this.message.set('Error al eliminar.'); }
    });
  }

}
