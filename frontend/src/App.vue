<template>
  <div>
    <content-header />
    <label>
      <input
        ref="inputRef"
        id="onlyMarvel"
        type="text"
        name="mcu"
        autocomplete
      />
    </label>
    <body-content />
    <footer id="footer"></footer>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, Ref } from 'vue-property-decorator';
  import ContentHeader from '@/views/main/header/TopContent.vue';
  import BodyContent from '@/views/main/body/BodyContent.vue';
  import Tagify from '@yaireo/tagify';

  @Component({
    components: { BodyContent, ContentHeader },
  })
  export default class App extends Vue {
    private tags = ['hi', 'hi'];
    @Ref() private readonly inputRef: HTMLInputElement;
    mounted() {
      const tagify = new Tagify(this.inputRef, {
        whitelist: [
          { value: 'ironman', code: 'im' },
          { value: 'antman', code: 'am' },
          { value: 'captain america', code: 'ca' },
          { value: 'thor', code: 'th' },
          { value: 'spiderman', code: 'sm' },
        ],
        enforceWhitelist: true,
      });
      tagify.on('add', function(e) {
        console.log(e.detail.data);
      });
    }
  }
</script>
