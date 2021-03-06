import BookButton from './Button.vue';

export default {
  title: 'Components/ButtonTs',
  component: BookButton,
};

//👇 We create a “template” of how args map to rendering
const Template = () => ({
  components: { BookButton },
  template: '<book-button :text="text" />',
  props: {
    text: { default: 'haha' },
  },
});

export const FirstStory = Template.bind({});
