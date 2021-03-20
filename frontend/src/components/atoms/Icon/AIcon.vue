<template>
  <Icon :icon="icons[iconName]" />
</template>
<script lang="ts">
  import { Icon } from '@iconify/vue';
  import baselineClear from '@iconify/icons-ic/baseline-clear';

  export default {
    name: 'AIcon', // @iconify/vue의 Icon 컴포넌트와 네이밍이 겹처 AIcon이라고 지음
    components: {
      Icon,
    },
    props: {
      iconName: {
        type: String,
        required: true,
      },
    },
    data() {
      return {
        // https://iconify.design/icon-sets/ic/
        icons: {
          baselineClear,
        },
      };
    },
  };
</script>
