<template>
  <div>{{ text }}</div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Tag',
    props: {
      text: {
        type: String,
        required: true,
      },
    },

    setup(props) {
      const { text } = props;
      return {
        text,
      };
    },
  });
</script>
