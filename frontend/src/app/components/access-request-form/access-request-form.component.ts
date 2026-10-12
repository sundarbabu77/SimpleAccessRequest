import { Component, EventEmitter, Output, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccessRequestService } from '../../services/access-request.service';

@Component({
  selector: 'app-access-request-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './access-request-form.component.html',
  styleUrl: './access-request-form.component.css',
})
export class AccessRequestFormComponent {
  /** Emits after a request was created successfully so the parent can refresh the list. */
  @Output() created = new EventEmitter<void>();

  private readonly fb = inject(FormBuilder);
  private readonly service = inject(AccessRequestService);

  readonly form = this.fb.nonNullable.group({
    account: ['', [Validators.required, Validators.maxLength(100)]],
    entitlement: ['', [Validators.required, Validators.maxLength(100)]],
    justification: ['', [Validators.maxLength(1000)]],
  });

  readonly submitting = signal(false);
  readonly errorMessage = signal<string | null>(null);
  readonly successMessage = signal<string | null>(null);

  submit(): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { account, entitlement, justification } = this.form.getRawValue();
    this.submitting.set(true);

    this.service
      .create({
        account: account.trim(),
        entitlement: entitlement.trim(),
        justification: justification.trim() || undefined,
      })
      .subscribe({
        next: (response) => {
          this.submitting.set(false);
          this.successMessage.set(`Request #${response.id} created.`);
          this.form.reset();
          this.created.emit();
        },
        error: (error: Error) => {
          this.submitting.set(false);
          this.errorMessage.set(error.message);
        },
      });
  }

  /** True when the control was touched and failed validation (drives inline error text). */
  hasError(controlName: 'account' | 'entitlement' | 'justification'): boolean {
    const control = this.form.controls[controlName];
    return control.invalid && (control.touched || control.dirty);
  }
}
