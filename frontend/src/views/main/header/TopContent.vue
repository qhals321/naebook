<template>
  <header id="header">
    <div class="content-wrap header-content-wrap">
      <div class="content-header-wrap row">
        <top-logo />
        <div class="content-header-bx row">
          <keyword-search />
          <top-notice />
        </div>
      </div>
      <top-nav />
    </div>
  </header>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import TopNav from '@/views/main/header/TopNav.vue';
import KeywordSearch from '@/components/search/KeywordSearch.vue';
import ContentNav from '@/views/main/header/TopNav.vue';
import TopNotice from '@/views/main/header/TopNotice.vue';
import TopLogo from '@/views/main/header/TopLogo.vue';
@Component({
  components: { TopLogo, TopNotice, ContentNav, KeywordSearch, TopNav }
})
export default class TopContent extends Vue {}
</script>
