import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private api = 'http://localhost:8080/products';

  constructor(
    private http: HttpClient
  ) {}

  create(data: any) {
    return this.http.post(this.api, data);
  }

  search(name = '', page = 0, size = 5) {
    const optName: string = name ? `name=${name.trim()}&` : '';
    return this.http.get<any>(`${this.api}?${optName}page=${page}&page-size=${size}`);
  }

  findById(id: string): Observable<any> {
    return this.http.get(`${this.api}/${id}`);
  }

  findInputs(id: string): Observable<any> {
    return this.http.get(`${this.api}/${id}/inputs`)
  }

  update(id: string, data: any) {
    return this.http.put(`${this.api}/${id}`, data);
  }
  
  delete(id: string) {
    return this.http.delete(`${this.api}/${id}`);
  }
}