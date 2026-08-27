type UserCreateForm = {
  role: FormControl<string>; // valeur = UserRoleEnum ('TECHNICIAN' | 'MANAGER' | 'DIRECTOR')
  firstName: FormControl<string>;
  lastName: FormControl<string>;
  email: FormControl<string>;
  phoneNumber: FormControl<string>;
  password: FormControl<string>;
  rank: FormControl<number>;
};
