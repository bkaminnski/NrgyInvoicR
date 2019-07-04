export class FixedFees {

  constructor(
    public subscriptionFee: number = 0,
    public networkFee: number = 0
  ) { }

  public static cloned(fixedFees: FixedFees): FixedFees {
    return new FixedFees(
      fixedFees.subscriptionFee,
      fixedFees.networkFee
    );
  }
}
