import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { AccessRequest } from '../../models/access-request.model';

/** Presentational component: renders whatever the parent passes in. */
@Component({
  selector: 'app-access-request-list',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './access-request-list.component.html',
  styleUrl: './access-request-list.component.css',
})
export class AccessRequestListComponent {
  @Input() requests: AccessRequest[] = [];
  @Input() loading = false;
  @Input() errorMessage: string | null = null;
}
