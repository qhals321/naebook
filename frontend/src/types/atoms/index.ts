export type LinkType = 'link' | 'a';

export type LinkOption = {
  type?: LinkType;
  class?: string;
  path: string;
  label: string;
};

export type InputType = 'text'
