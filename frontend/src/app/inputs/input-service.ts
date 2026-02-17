import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Input } from './input-model';

@Injectable({
  providedIn: 'root'
})
export class InputService {

  private api = 'http://localhost:8080/inputs';

  constructor(private http: HttpClient) {}

  create(data: Input): Observable<void> {
    return this.http.post<void>(this.api, data);
  }

  findById(id: string): Observable<Input> {
    return this.http.get<Input>(`${this.api}/${id}`);
  }

  search(name = '', page = 0, pageSize = 5): Observable<any> {
    const optName: string = name ? `name=${name.trim()}&` : '';
    return this.http.get(`${this.api}?${optName}page=${page}&page-size=${pageSize}`);
  }

  searchAll(name = ''): Observable<any> {
    const optName: string = name ? `name=${name.trim()}&` : '';
    return this.http.get(`${this.api}?${optName}`);
  }

  findProducts(id: string): Observable<any> {
    return this.http.get(`${this.api}/${id}/products`)
  }

  update(id: string, data: Input): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, data);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  findProductsUsingInput(id: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/${id}/products`);
  }
}
