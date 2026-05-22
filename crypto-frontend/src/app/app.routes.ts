import { Routes } from '@angular/router';
import { CryptoList } from './pages/crypto-list/crypto-list';
import { CryptoDetail } from './pages/crypto-detail/crypto-detail';
import { Watchlist } from './pages/watchlist/watchlist';

export const routes: Routes = [
  { path: '', component: CryptoList },
  { path: 'item/:id', component: CryptoDetail },
  { path: 'watchlist', component: Watchlist },
  { path: '**', redirectTo: '' }
];
