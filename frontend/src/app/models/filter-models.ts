export interface Filter {
  priceFrom: number | undefined,
  priceTo: number | undefined,
  rating: number[] | undefined;
  productName: string,
  instructorName: string
}
