type VersionType = {
  idVersionType: number;
  designation: string; // 1 <= size <= 127
  code: string; // 1 <= size <= 3
  urgencyMalus: number | null;
};
