import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { WatchlistService } from '../../services/watchlist';

@Component({
  selector: 'app-watchlist',
  standalone: true,
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './watchlist.html'
})
export class Watchlist implements OnInit {

  watchlistService = inject(WatchlistService);

  ngOnInit(): void {
    this.watchlistService.loadWatchlist();
  }

}
