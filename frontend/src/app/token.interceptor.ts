import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public sessionService: SessionService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.sessionService.isLoggedIn()) {
      request = request.clone({
        setHeaders: {
          'Content-Type' : 'application/json',
          'Authorization' : 'Bearer ' + localStorage.getItem('token')
        }
      });
    } else{
        return null;
    }
    return next.handle(request); 
  }
}
