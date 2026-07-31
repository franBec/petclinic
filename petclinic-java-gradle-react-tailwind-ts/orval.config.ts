import { defineConfig } from 'orval';

export default defineConfig({
  petclinic: {
    input: {
      target: 'src/main/resources/openapi.yaml',
    },
    output: {
      mode: 'tags-split',
      target: 'src/main/webapp/app/__generated__/api/endpoints.ts',
      schemas: 'src/main/webapp/app/__generated__/api/types',
      client: 'react-query',
      mock: false,
      override: {
        mutator: {
          path: 'src/main/webapp/app/api/mutator.ts',
          name: 'customAxios',
        },
      },
    },
  },
});
