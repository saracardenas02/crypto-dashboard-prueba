import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CurrencyPipe, DecimalPipe, NgClass } from '@angular/common';
import { CryptoService } from '../../services/crypto';
import { WatchlistService } from '../../services/watchlist';

@Component({
  selector: 'app-crypto-list',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, DecimalPipe, NgClass],
  templateUrl: './crypto-list.html'
})
export class CryptoList implements OnInit {

  cryptoService = inject(CryptoService);
  watchlistService = inject(WatchlistService);

  ngOnInit(): void {
    this.cryptoService.loadAll();
  }

}
