import BookButton from './Button.vue';

// 이 스토리의 설정입니다. 스토리 제목, 사용되는 컴포넌트.. 등을
export default {
  title: 'Components/ButtonJS',
  component: BookButton,
};

const Template = (args, { argTypes }) => ({
  props: Object.keys(argTypes),
  components: { BookButton },
  template: '<book-button :text="text" />',
});

export const FirstStory = Template.bind({});
FirstStory.args = {
  text: 'haha',
};
