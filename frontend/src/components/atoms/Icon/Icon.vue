<template>
  <inline-icon class="icon" :icon="icons[iconName]" />
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { InlineIcon } from '@iconify/vue';
  import baselineClear from '@iconify/icons-ic/baseline-clear';
  import notificationNew from '@iconify-icons/carbon/notification-new';
  import notification from '@iconify-icons/carbon/notification';
  import profile from '@iconify-icons/bi/person-circle';

  export default defineComponent({
    name: 'Icon',
    components: {
      InlineIcon,
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
          notificationNew,
          notification,
          profile,
        },
      };
    },
  });
</script>
<style scoped>
  .icon {
    cursor: pointer;
  }
</style>
