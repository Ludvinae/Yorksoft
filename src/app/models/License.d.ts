type License = {
  idLicense: number;
  licenseNumber: string; // 1 <= size <= 127
  expirationDate: Date | null;
  idSoftware: number;
  idClient: number | null;
}
