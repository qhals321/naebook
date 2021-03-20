export type InputOption = {
	id: string;
	class?: string;
	type?: InputType;
	placeholder?: string;
}

export type InputType = 'text' | 'checkbox'; // TODO etc

export type LabelOption = {
	for: string;
	label?: string;
}
