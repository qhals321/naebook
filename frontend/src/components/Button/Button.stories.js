import BookButton from './Button.vue';

export default {
  title: 'Components/Button',
  component: BookButton,
  argTypes: {},
};

//👇 We create a “template” of how args map to rendering
const Template = (args, { argTypes }) => ({
  props: Object.keys(argTypes),
  components: { BookButton },
  template: '<book-button :text="text" />',
});

export const FirstStory = Template.bind({});
FirstStory.args = { text: 'hahaha' };
