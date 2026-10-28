import { Component } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KpiCard } from './kpi-card';

@Component({
  imports: [KpiCard],
  template: `
    <app-kpi-card id="passive" title="Passive" [value]="1" />
    <app-kpi-card id="clickable" title="Clickable" [value]="2" (cardClick)="clicks = clicks + 1" />
  `,
})
class Host {
  clicks = 0;
}

describe('KpiCard', () => {
  let fixture: ComponentFixture<Host>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ imports: [Host] }).compileComponents();
    fixture = TestBed.createComponent(Host);
    await fixture.whenStable();
  });

  const card = (id: string): HTMLElement =>
    fixture.nativeElement.querySelector(`#${id} .card`) as HTMLElement;

  it('is not clickable when the parent does not listen to cardClick', () => {
    expect(card('passive').classList.contains('cursor-pointer')).toBe(false);
  });

  it('is clickable and emits cardClick when the parent listens to it', () => {
    expect(card('clickable').classList.contains('cursor-pointer')).toBe(true);

    card('clickable').click();

    expect(fixture.componentInstance.clicks).toBe(1);
  });
});
