type KnowledgeCreateModalDto = {
  idTheme: number;
  themeName: string;

  idVersion: number;
  versionLabel: string;

  ticketTitle?: string;
  ticketDescription?: string;
}
