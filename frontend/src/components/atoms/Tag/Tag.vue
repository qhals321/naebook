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
    padding: 5px;
    line-height: 34px; /* 주의: height랑 통일 안되어있음. li태그랑 함께 쓸 때 공백주려고 넣은 line-height */ 
    font-size: 0.75rem;
    border: 1px solid #c4c4c4;
    border-radius: 7px;
  }
</style>
