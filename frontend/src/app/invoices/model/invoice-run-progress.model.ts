export class InvoiceRunProgress {

  constructor(
    public numberOfInvoicesToGenerate?: number,
    public numberOfSuccesses?: number,
    public numberOfFailures?: number) {
  }

  static cloned(invoiceRunProgress: InvoiceRunProgress): InvoiceRunProgress {
    return new InvoiceRunProgress(
      invoiceRunProgress.numberOfInvoicesToGenerate,
      invoiceRunProgress.numberOfSuccesses,
      invoiceRunProgress.numberOfFailures
    );
  }
}
