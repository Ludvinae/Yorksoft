import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'userRoleLabel',
  standalone: true,
})
export class UserRoleLabel implements PipeTransform {
  transform(role: string | null | undefined, technicianRank?: number | null): string {
    switch (role) {
      case 'DIRECTOR':
        return 'Directeur';

      case 'MANAGER':
        return 'Manager';

      case 'ADMIN':
        return 'Administrateur';

      case 'CLIENT':
        return 'Client';

      case 'TECHNICIAN':
        return technicianRank != null ? `Technicien N${technicianRank}` : 'Technicien';

      default:
        return 'Utilisateur';
    }
  }
}
