// Mirrors backend dto/details/KnowledgeArticleDto (GET /knowledge/articles)
type KnowledgeArticle = {
  idKnowledge: number;
  subject: string;
  description: string | null;
  resolution: string | null;
  idTheme: number;
  themeName: string;
  authorName: string | null;
  versions: string[];
  procedures: KnowledgeArticleProcedure[];
};

type KnowledgeArticleProcedure = {
  idProcedure: number;
  title: string;
  content: string;
  creationDate: string | null;
  modificationDate: string | null;
  authorName: string | null;
};

// Body sent to PUT /knowledge/{id} from the knowledge base page.
// versionIds is left null so the backend keeps the article's current versions.
type KnowledgeArticleUpdate = {
  subject: string;
  description: string;
  resolution: string;
  idTheme: number;
  versionIds: null;
};
