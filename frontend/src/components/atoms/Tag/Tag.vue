<template>
  <span class="tag">
    {{ text }} <icon class="tag-icon clear" iconName="baselineClear" />
  </span>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Icon from '@/components/atoms/Icon/Icon.vue';

  export default defineComponent({
    name: 'Tag',
    components: {
      Icon,
    },
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
<style scoped>
  span.tag {
    padding: 1px 8px;
    font-size: 0.75rem;
    border: 1px solid #c4c4c4;
    border-radius: 7px;
  }
</style>
