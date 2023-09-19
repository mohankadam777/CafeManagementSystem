export class User {
    constructor(private _token: string, private expirationTime?: Date) {}
  
    public getToken(): string | null {
      if (this.expirationTime && this.expirationTime > new Date()) {
        return this._token;
      } else {
        return null;
      }
    }
    public getExpirationTime(): Date | null {
      return this.expirationTime ? this.expirationTime : null;
    }
  }