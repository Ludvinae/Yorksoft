

export function toCreateDto(form: SoftwareForm): SoftwareCreateDto {
  return {
    name: form.name,
    description: form.description,
    idSoftwareType: form.idSoftwareType,
  };
}

export function toForm(dto: SoftwareResponseDto): SoftwareForm {
  return {
    name: dto.name,
    description: dto.description,
    idSoftwareType: dto.type.idSoftwareType,
  };
}

