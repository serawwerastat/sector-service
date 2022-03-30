export class SectorDataModel {
  public userId: string;
  public name: string;
  public sectors: string[];
  public agreementAccepted: boolean;

  constructor(name: string, sectors: string[], userId: string, agreementAccepted: boolean) {
    this.name = name;
    this.sectors = sectors;
    this.userId = userId;
    this.agreementAccepted = agreementAccepted;
  }
}
