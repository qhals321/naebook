<template>
  <inline-icon
    :class="[option.color, option.size]"
    :icon="icons[option.name]"
  />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import { InlineIcon } from '@iconify/vue';
  import notificationNew from '@iconify-icons/carbon/notification-new';
  import notification from '@iconify-icons/carbon/notification';
  import profile from '@iconify-icons/bi/person-circle';

  const option = {
    color: 'navy',
    size: 'regular',
  };

  export default defineComponent({
    name: 'Icon',
    components: { InlineIcon },
    props: ['iconOption'],
    data() {
      return {
        icons: {
          notificationNew,
          notification,
          profile,
        },
      };
    },
    setup(props) {
      return {
        option: {
          ...option,
          ...props.iconOption,
        },
      };
    },
  });
</script>

<style scoped>
  .regular {
    font-size: 1.5rem;
  }
  .navy {
    color: var(--color-navy);
  }
  .red {
    color: crimson;
  }
</style>
