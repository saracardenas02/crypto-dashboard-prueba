export interface CryptoItem {
  id: string;
  symbol: string;
  name: string;
  currentPrice: number;
  marketCap: number;
  priceChangePercentage24h: number;
  totalVolume: number;
  imageUrl: string;
  lastUpdated: string;
}
