**Libraries**
>Below are the libraries I used for the project.
>- Retrofit: For network related activities,
>- Glide: For image lazy loading + amazing cache system
>- Hilt: For my Dependency Injection, so I can focus more on the task
>- Mavericks: Android MVI framework, Helps abstract my UI, activity, viewModel.
>- Epoxy: Allows me to build complex ui easily on recyclerView
>- Navigation: Android arch components for navigation
>- Mockito: Unit testing library


**Approach**
>- Overall, I broke down the problems to small pieces called `State`. State simply represents the brain of a screen. `Event` are anything that just happened that State should know about.
>- For lazy loading my image and caching, I went with glide. As it built to better handle this as it focuses on smooth scrolling
>- For the rating view, i used textView to hold the string value, progressbar gave it a background and progressDrawable, then i apply tints when i call `setRating`


**Assumptions**
>- I assumed the user's network would be fine to an extent as i implemented a more subtle error handling + retry
>- `release_date` is sometimes `null`, I proceed to make it optional then display `n/a` in this case.
>- `runtime` isn't available for `/movie/popular`, so I made it optional then set a default of `n/a` when it's not available. But I fetch it once user navigates to movie details fragment.
