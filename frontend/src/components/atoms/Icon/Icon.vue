<template>
  <iconify-icon :icon="icons[iconName]" />
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import IconifyIcon from '@iconify/vue';
  import baselineClear from '@iconify/icons-ic/baseline-clear';

  export default defineComponent({
    name: 'Icon',
    components: {
      IconifyIcon,
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
          baselineClear: baselineClear,
        },
      };
    },
  });
</script>
