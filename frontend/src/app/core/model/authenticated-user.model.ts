import * as jwt_decode from 'jwt-decode';

export class AuthenticatedUser {
  public readonly name: string;
  public readonly email: string;
  private expiryDate: Date;

  constructor(public authenticationToken: string) {
    const decodedToken = jwt_decode(authenticationToken);
    this.name = decodedToken.name;
    this.email = decodedToken.email;
    this.expiryDate = new Date(0);
    this.expiryDate.setUTCSeconds(decodedToken.exp);
  }

  public isTokenExpired(): boolean {
    return new Date().getTime() > this.expiryDate.getTime();
  }
}
