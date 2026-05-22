import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CurrencyPipe, DecimalPipe, DatePipe } from '@angular/common';
import { CryptoService } from '../../services/crypto';
import { WatchlistService } from '../../services/watchlist';

@Component({
  selector: 'app-crypto-detail',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, DecimalPipe],
  templateUrl: './crypto-detail.html'
})
export class CryptoDetail implements OnInit, OnDestroy {

  cryptoService = inject(CryptoService);
  watchlistService = inject(WatchlistService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.cryptoService.loadById(id);
  }

  ngOnDestroy(): void {
    this.cryptoService.clearSelected();
  }

}
