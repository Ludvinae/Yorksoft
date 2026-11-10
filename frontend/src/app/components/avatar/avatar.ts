import { Component, computed, input } from '@angular/core';

/**
 * User initials in a colored square, generated locally. Replaces the ui-avatars.com
 * images: loading them sent every visitor's IP and the displayed names to a third party.
 * The caller sets size, shape and text size on the host, e.g. class="w-12 h-12 rounded-full".
 */
@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.html',
  host: {
    class: 'inline-flex shrink-0 items-center justify-center overflow-hidden bg-neutral text-neutral-content font-semibold select-none',
    'aria-hidden': 'true',
  },
})
export class Avatar {
  readonly name = input<string | null | undefined>('');

  protected readonly initials = computed(() =>
    (this.name() ?? '')
      .trim()
      .split(/\s+/)
      .filter(Boolean)
      .slice(0, 2)
      .map((word) => word[0].toUpperCase())
      .join(''),
  );
}
