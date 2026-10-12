import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import {
  AccessRequest,
  ApiError,
  CreateAccessRequestPayload,
  CreateAccessRequestResponse,
} from '../models/access-request.model';

/** Single place for all access-request API calls and API error translation. */
@Injectable({ providedIn: 'root' })
export class AccessRequestService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = '/api';

  create(payload: CreateAccessRequestPayload): Observable<CreateAccessRequestResponse> {
    return this.http
      .post<CreateAccessRequestResponse>(`${this.baseUrl}/request`, payload)
      .pipe(catchError((error) => this.handleError(error)));
  }

  list(): Observable<AccessRequest[]> {
    return this.http
      .get<AccessRequest[]>(`${this.baseUrl}/requests`)
      .pipe(catchError((error) => this.handleError(error)));
  }

  /** Converts any HTTP failure into an Error carrying a user-friendly message. */
  private handleError(error: HttpErrorResponse): Observable<never> {
    return throwError(() => new Error(this.toMessage(error)));
  }

  private toMessage(error: HttpErrorResponse): string {
    if (error.status === 0) {
      return 'Cannot reach the server. Please check that the backend is running.';
    }
    const body = error.error as Partial<ApiError> | null;
    if (body?.fieldErrors && Object.keys(body.fieldErrors).length > 0) {
      return Object.values(body.fieldErrors).join('. ');
    }
    if (body?.message) {
      return body.message;
    }
    return `Unexpected error (HTTP ${error.status}). Please try again.`;
  }
}
