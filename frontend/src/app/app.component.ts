import { Component, OnInit, inject, signal } from '@angular/core';
import { AccessRequestFormComponent } from './components/access-request-form/access-request-form.component';
import { AccessRequestListComponent } from './components/access-request-list/access-request-list.component';
import { AccessRequest } from './models/access-request.model';
import { AccessRequestService } from './services/access-request.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AccessRequestFormComponent, AccessRequestListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  private readonly service = inject(AccessRequestService);

  readonly requests = signal<AccessRequest[]>([]);
  readonly loading = signal(false);
  readonly errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.service.list().subscribe({
      next: (requests) => {
        this.requests.set(requests);
        this.loading.set(false);
      },
      error: (error: Error) => {
        this.errorMessage.set(error.message);
        this.loading.set(false);
      },
    });
  }
}
