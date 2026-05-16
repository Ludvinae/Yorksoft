type Client = {
  idAppUser: number;
  firstName: string | null; // size <= 127
  lastName: string | null; // size <= 127
  email: string; // size <= 127
  phoneNumber: string | null; // size <= 31
  importance: number;
};
