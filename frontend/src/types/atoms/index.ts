export type LinkType = 'link' | 'a';

export type LinkOption = {
  type?: LinkType;
  class?: string;
  path: string;
  label: string;
};

export type LabelOption = {
  for: string;
  label?: string;
}

export type InputOption = {
  id: string;
  class?: string;
  type?: InputType;
  placeholder?: string;
}

export type InputType = 'text'
