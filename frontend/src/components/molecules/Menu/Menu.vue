<template>
  <div class="menu-link-container">
    <menu-link
      class="menu-link"
      v-for="(menu, idx) in menuList"
      :key="idx"
      :path="menu.path"
      :name="menu.name"
    >
      <template v-if="menu.icon">
        <icon class="pointer" :icon-name="menu.icon" />
        <span class="menu-name pointer">{{ menu.name }}</span>
      </template>
    </menu-link>
  </div>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import MenuLink from '@/components/atoms/Link/Link.vue';
  import Icon from '@/components/atoms/Icon/Icon.vue';
  export default defineComponent({
    name: 'Menu',
    components: { MenuLink, Icon },
    props: {
      menuList: {
        type: Object as PropType<
          {
            name: string;
            path: string;
            icon?: 'notificationNew' | 'notification' | 'profile';
          }[]
        >,
        required: true,
      },
    },
  });
</script>

<style scoped>
  .menu-link:not(:last-child) {
    padding-right: 32px;
  }

  .menu-link {
    font-size: var(--font-title-semi-micro);
    display: inline-flex;
    justify-content: center;
    align-items: center;
  }

  .pointer {
    cursor: pointer;
  }

  .menu-name {
    color: var(--color-dark-gray);
    font-size: var(--font-title-micro);
    padding-left: 4px;
  }
</style>
