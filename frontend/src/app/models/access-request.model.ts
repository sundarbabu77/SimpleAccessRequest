/** Payload sent to POST /api/request. */
export interface CreateAccessRequestPayload {
  account: string;
  entitlement: string;
  justification?: string;
}

/** Response of POST /api/request. */
export interface CreateAccessRequestResponse {
  id: number;
  status: string;
  createdAt: string;
}

/** Item returned by GET /api/requests. */
export interface AccessRequest {
  id: number;
  account: string;
  entitlement: string;
  justification: string | null;
  status: string;
  createdAt: string;
}

/** Error body returned by the backend. */
export interface ApiError {
  status: number;
  message: string;
  fieldErrors?: Record<string, string>;
}
