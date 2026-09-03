type AppUser = {
  idAppUser: number;
  firstName: string | null; // size <= 127
  lastName: string | null; // size <= 127
  email: string; // 1 <= size <= 127
  phoneNumber: string | null; // size <= 31
};
