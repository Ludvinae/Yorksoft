
export function toCreateDto(form: SoftwareTypeForm): SoftwareTypeCreateDto {
  return {
    designation: form.designation
  };
}

export function toForm(dto: SoftwareTypeResponseDto): SoftwareTypeForm {
  return {
    idSoftwareType: dto.idSoftwareType,
    designation: dto.designation
  };
}
