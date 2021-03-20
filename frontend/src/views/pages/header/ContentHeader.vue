<template>
  <header style="width: 100vw;">
    <Logo />
    <Label :option="{ label: '', for: 'id' }" />
    <Input
      :option="{
        id: 'id',
      }"
    />
  </header>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Logo from '@/components/atoms/logo/Logo.vue';
  import Input from '@/components/atoms/input/Input.vue';
  import Label from '@/components/atoms/label/Label.vue';
  export default defineComponent({
    name: 'ContentHeader',
    components: { Logo, Input, Label },
  });
</script>

<style scoped>
  header {
    background-color: var(--color-navy);
  }
</style>
