import { inject, Injectable } from '@angular/core';
import { TechnicianService } from '../api/technician-service';
import { ManagerService } from '../api/manager-service';
import { DirectorService } from '../api/director-service';
import { UserRoleEnum } from '../../models/enum/UserRoleEnum';

@Injectable({
  providedIn: 'root',
})
export class UserFormService {
  private technicianService = inject(TechnicianService);
  private managerService = inject(ManagerService);
  private directorService = inject(DirectorService);

  save(formValue: UserCreateForm) {
    const base = {
      idAppUser: 0,
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email,
      phoneNumber: formValue.phoneNumber,
      password: formValue.password,
    };

    switch (formValue.role) {
      case UserRoleEnum.TECHNICIAN:
        return this.technicianService.create({
          ...base,
          rank: formValue.rank,
        });
      case UserRoleEnum.MANAGER:
        return this.managerService.create(base);
      case UserRoleEnum.DIRECTOR:
        return this.directorService.create(base);
      default:
        throw new Error(`Rôle inconnu : ${formValue.role}`);
    }
  }
}
